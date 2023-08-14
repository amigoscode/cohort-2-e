import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";
import {login as performLogin} from "../../services/user.js";
import jwtDecode from "jwt-decode";

const AuthContext = createContext({})
const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);

    const setUserFromToken =  () => {
        let jwtToken = localStorage.getItem("access_token");
        if (jwtToken){
            jwtToken = jwtDecode(jwtToken);
            setUser({
                username: jwtToken.sub,
                roles: jwtToken.scopes
            });
        }
    }

    useEffect( ()=>{
        setUserFromToken()
    }, [])


    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"]
                localStorage.setItem("access_token", jwtToken);
                const decodeJwtToken = jwtDecode(jwtToken);
                setUser({
                    username: decodeJwtToken.sub,
                    roles: decodeJwtToken.scopes
                })
                resolve(res);
            }).catch(err => {


                reject(err)
            })
        })
    }

    const logOut = () => {
        localStorage.removeItem("access_token");
        setUser(null);
    }
    const isUserAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
        const {exp: expiration} = jwtDecode(token);
        if (Date.now() > expiration * 1000) {
            logOut();
            return false;
        }
        return true;
    }

    return (
        <AuthContext.Provider value={
            {
                user,
                login,
                logOut,
                isUserAuthenticated,
                setUserFromToken
            }
        }>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext)
export default AuthProvider;

