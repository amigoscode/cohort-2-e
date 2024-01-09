import {Wrap, WrapItem, Spinner, Text, Tooltip, IconButton} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getProviders} from "./services/provider.js";
import CardWithImage from "./components/provider/providerCard.jsx";
import CreateProviderDrawer from "./components/provider/CreateProviderDrawer.jsx";
import {errorNotification} from "./services/notification.js";
import {Flex} from '@chakra-ui/react';
import {
    ChevronRightIcon,
    ChevronLeftIcon
  } from "@chakra-ui/icons";
import './index.css';

const  Provider = () => {
    const [providers, setProviders] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPage, setTotalPage] = useState(1);
    const [searchTerm, setSearchTerm] = useState('');

    const itemsPerPage = 2;

    
    const fetchProviders = ()=> {
        setLoading(true);
        getProviders(searchTerm,currentPage-1, itemsPerPage).then(response => {
            
            setTotalPage(response.data.totalPages)
            setProviders(response.data.providers)
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
    }, [currentPage, totalPage, searchTerm])

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
                <div className="container">
                    <input
                        type="text"
                        placeholder="Search name..."
                        value={searchTerm}
                        onChange={
                            (e) => {
                                setSearchTerm(e.target.value)
                                setCurrentPage(1)
                            }
                        }
                    />
                </div>                
                <Text mt={5}>No costumers Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <CreateProviderDrawer
                fetchProviders = {fetchProviders}
            />
            <div className="container">
                <input
                    type="text"
                    placeholder="Search name..."
                    value={searchTerm}
                    onChange={
                        (e) => {
                            setSearchTerm(e.target.value)
                            setCurrentPage(1)
                        }
                    }
                />
            </div>            
            <Wrap justify={"center"} spacing={"30px"}>
                {providers.map((provider, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...provider}
                            imageNumber={index}
                            fetchProviders = {fetchProviders}
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
export default Provider;