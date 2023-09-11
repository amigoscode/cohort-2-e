import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getPatients} from "./services/patient.js";
import CardWithImage from "./components/patient/patientCard.jsx";
import {errorNotification} from "./services/notification.js";


const  Patient = () => {
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const fetchPatients = ()=> {
        setLoading(true);
        getPatients().then(response => {
            setPatients(response.data)
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
        fetchPatients()
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

    if(patients.length <=0){
        return (
            <SidebarWithHeader>
                <Text mt={5}>No costumers Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <Wrap justify={"center"} spacing={"30px"}>
                {patients.patients.map((patient, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...patient}
                            imageNumber={index}
                            fetchPatients = {fetchPatients}
                        />
                    </WrapItem>

                ))}
            </Wrap>

        </SidebarWithHeader>
    )

}
export default Patient;