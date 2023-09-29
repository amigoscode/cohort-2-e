import {
    Avatar,
    Box,
    Button, Center,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Flex, Heading, Image, Stack, Tag, Text, useColorModeValue, useDisclosure
} from "@chakra-ui/react";

const GetPatientDetailDrawer = ({initialValues, patientId}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()

    let dob = new Date(initialValues.dob).toLocaleDateString()
    let createdAt = new Date(initialValues.createdAt).toLocaleString()
    return (
        <>
            <Button
                bg={'gray.200'}
                color={'black'}
                rounded={'full'}
                _hover={{
                    transform: 'translateY(-2px)',
                    boxShadow: 'lg'
                }}

                onClick={onOpen}

            > Details</Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Patient Details</DrawerHeader>

                    <DrawerBody>
                        <Center py={6}>
                            <Box
                                maxW={'300px'}
                                minW={'300px'}
                                w={'full'}
                                m={2}
                                bg={useColorModeValue('white', 'gray.800')}
                                boxShadow={'lg'}
                                rounded={'md'}
                                overflow={'hidden'}>
                                <Image
                                    h={'120px'}
                                    w={'full'}
                                    src={
                                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                                    }
                                    objectFit={'cover'}
                                />
                                <Flex justify={'center'} mt={-12}>
                                    <Avatar
                                        size={'xl'}
                                        src={
                                            `https://randomuser.me/api/portraits/men/${initialValues.imageNumber}.jpg`
                                        }
                                        alt={'Author'}
                                        css={{
                                            border: '2px solid white',
                                        }}
                                    />
                                </Flex>

                                <Box p={6}>
                                    <Stack spacing={2} align={'center'} mb={5}>
                                        <Tag borderRadius={"full"}>{patientId}</Tag>
                                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                                            {name}
                                        </Heading>
                                        <Text color={'gray.500'}>FULL NAME: {initialValues.fullName}</Text>
                                        <Text color={'gray.500'}>MRN: {initialValues.mrn}</Text>
                                        <Text color={'gray.500'}>GENDER: {initialValues.gender}</Text>
                                        <Text color={'gray.500'}>DOB: {dob}</Text>
                                        <Text color={'gray.500'}>CREATED: {createdAt}</Text>


                                    </Stack>
                                </Box>
                            </Box>
                        </Center>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button type='submit' form='my-form'>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>

    )
}

export default GetPatientDetailDrawer
