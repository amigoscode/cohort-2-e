import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getSchedules} from "./services/schedule.js";
import CardWithImage from "./components/schedule/card.jsx";
import {errorNotification} from "./services/notification.js";


const  Home = () => {
    const [schedules, setSchedules] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const fetchSchedules = ()=> {
        setLoading(true);
        getSchedules().then(response => {
            setSchedules(response.data)
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
        fetchSchedules()
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
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(schedules.length <=0){
        return (
            <SidebarWithHeader>
                <Text mt={5}>No schedules Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <Wrap justify={"center"} spacing={"30px"}>
                {schedules.schedules.map((schedule, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...schedule}
                            imageNumber={index}
                            fetchSchedules = {fetchSchedules}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )

}
export default Home;