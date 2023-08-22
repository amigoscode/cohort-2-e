import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getProviders} from "./services/provider.js";
import CardWithImage from "./components/provider/card.jsx";
import CreateProviderDrawer from "./components/provider/CreateProviderDrawer.jsx";
import {errorNotification} from "./services/notification.js";


const  Provider = () => {
    const [providers, setProviders] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const fetchProviders = ()=> {
        setLoading(true);
        getProviders().then(response => {
            setProviders(response.data)
        }).catch(err =>{
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
                setLoading(false);
            }
        )
    }
    useEffect(()=>{
        fetchProviders()
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
                <CreateProviderDrawer
                    fetchProviders = {fetchProviders}
                />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(providers.length <=0){
        return (
            <SidebarWithHeader>
                <CreateProviderDrawer
                    fetchProviders = {fetchProviders}
                />
                <Text mt={5}>No costumers Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <CreateProviderDrawer
                fetchProviders = {fetchProviders}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {providers.providers.map((provider, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...provider}
                            imageNumber={index}
                            fetchProviders = {fetchProviders}
                        />
                    </WrapItem>

                ))}
            </Wrap>

        </SidebarWithHeader>
    )

}
export default Provider;