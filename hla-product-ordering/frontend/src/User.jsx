import {Wrap, WrapItem, Spinner, Text, Tooltip, IconButton} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";
import {useEffect, useState} from "react";
import {getUsers} from "./services/user.js";
import CardWithImage from "./components/user/userCard.jsx";
import CreateUserDrawer from "./components/user/CreateUserDrawer.jsx";
import {errorNotification} from "./services/notification.js";
import { Box, Flex, Spacer } from '@chakra-ui/react';
import {
    ChevronRightIcon,
    ChevronLeftIcon
  } from "@chakra-ui/icons";
const  User = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    // Extract the pageProviderDto from the response
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPage, setTotalPage] = useState(1);
    const [searchTerm, setSearchTerm] = useState('');


    const itemsPerPage = 3;
    // let totalPage = 1;


    const fetchUsers = ()=> {
        setLoading(true);
        getUsers(currentPage-1, itemsPerPage).then(response => {
            
            // setSearchTerm()
            const filteredUsers = response.data.users.filter(user =>
                user.name.toLowerCase().includes(searchTerm.toLowerCase())
            );
            console.log(filteredUsers);
            if (filteredUsers.length < response.data.users.length){
                setTotalPage(Math.ceil(filteredUsers.length/itemsPerPage))
            }
            else{
                setTotalPage(response.data.totalPages)
            }
            setUsers(filteredUsers)            
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
    }, [currentPage, totalPage ,searchTerm])

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
               <Text mt={5}>No users Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <CreateUserDrawer
                fetchUsers = {fetchUsers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {users.map((user, index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...user}
                            imageNumber={index}
                            fetchUsers = {fetchUsers}
                        />
                    </WrapItem>

                ))}
            </Wrap>

            <div>
            <input
                type="text"
                placeholder="Search by provider name"
                value={searchTerm}
                onChange={
                    (e) => {
                        setSearchTerm(e.target.value)
                        // fetchUsers()
                    }
                }
            />
            </div>            
            {/* <div>
                <Spacer/>
                <button
                    onClick={() => setCurrentPage(prevPage => Math.max(prevPage - 1, 1))}
                    disabled={currentPage === 1}>
                Previous
                </button>

                <span> Page {currentPage} </span>
                <button
                    onClick={() => setCurrentPage(prevPage => prevPage + 1)}
                    // You should handle the disabled state based on the total number of pages
                    disabled={currentPage === totalPage}>
                Next
                </button>
            </div> */}

            <Flex justifyContent="space-between" m={1} alignItems="center">
                {/* <Spacer /> */}
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
export default User;