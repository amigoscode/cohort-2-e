import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getUsers} from "./services/user.js";
import CardWithImage from "./components/user/card.jsx";
import CreateUserDrawer from "./components/user/CreateUserDrawer.jsx";
import {errorNotification} from "./services/notification.js";


const  User = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const fetchUsers = ()=> {
        setLoading(true);
        getUsers().then(response => {
            setUsers(response.data)
        }).catch(err =>{
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
                console.log(users)
                setLoading(false);
            }
        )
    }
    useEffect(()=>{
      fetchUsers()
    }, [])

    if(loading){
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
            )
    }
    if(error){
        return (
            <SidebarWithHeader>
                <CreateUserDrawer
                    fetchUsers = {fetchUsers}
                />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(users.length <=0){
        return (
            <SidebarWithHeader>
                <CreateUserDrawer
                    fetchUsers = {fetchUsers}
                />
               <Text mt={5}>No costumers Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <CreateUserDrawer
                fetchUsers = {fetchUsers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {users.users.map((user, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...user}
                            imageNumber={index}
                            fetchUsers = {fetchUsers}
                        />
                    </WrapItem>

                ))}
            </Wrap>

        </SidebarWithHeader>
    )

}
export default User;