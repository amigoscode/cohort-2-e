import {Wrap, WrapItem, Spinner, Text, Tooltip, IconButton} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getSchedules} from "./services/schedule.js";
import CardWithImage from "./components/schedule/card.jsx";
import {errorNotification} from "./services/notification.js";
import {Flex} from '@chakra-ui/react';
import {
    ChevronRightIcon,
    ChevronLeftIcon
  } from "@chakra-ui/icons";


const itemsPerPage = 2;
  
const  Home = () => {
    const [schedules, setSchedules] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPage, setTotalPage] = useState(1);
    const fetchSchedules = ()=> {
        setLoading(true);
        getSchedules(currentPage-1,itemsPerPage).then(response => {
            setTotalPage(response.data.totalPages)
            setSchedules(response.data)
        }).catch(err =>{
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
                setLoading(false);
                console.log(schedules)
            }
        )
    }
    useEffect(()=>{
        fetchSchedules()
    }, [currentPage, totalPage])

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
            <Flex justifyContent="space-between" m={1} alignItems="center">
                <Tooltip label="Previous Page">
                    <IconButton
                    onClick={() => setCurrentPage(prevPage => Math.max(prevPage - 1, 1))}
                    isDisabled={currentPage === 1}
                    icon={<ChevronLeftIcon h={6} w={6} />}
                    />
                </Tooltip>



                <Text flexShrink="0" mr={8}>
                    Page{" "}
                    <Text fontWeight="bold" as="span">
                    {currentPage}
                    </Text>{" "}
                    of{" "}
                    <Text fontWeight="bold" as="span">
                    {totalPage}
                    </Text>
                </Text>




                <Tooltip label="Next Page">
                    <IconButton
                    onClick={() => setCurrentPage(prevPage => prevPage + 1)}
                    isDisabled={currentPage === totalPage}
                    icon={<ChevronRightIcon h={6} w={6} />}
                    />
                </Tooltip>
                
            </Flex>            
        </SidebarWithHeader>
    )

}
export default Home;