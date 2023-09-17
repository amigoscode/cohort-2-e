import {
    Heading,
    Box,
    Center,
    Stack,
    Tag,
    TagLabel,
    useDisclosure, useColorModeValue,Flex, Avatar,
} from '@chakra-ui/react';
import {useRef} from "react";
import UpdateScheduleDrawer from "./UpdateScheduleDrawer.jsx";


export default function CardWithImage({id, status, version, note, patient, imageNumber, fetchSchedules}) {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const cancelRef = useRef()
    const randomUserGender = patient.gender === "MALE" ? "men" : "women";
    let color = "grey.200";
    if(status === "REVIEWED"){
        color = "orange.200";
    }else if(status === "APPROVED_AND_EMAIL_SENT"){
        color = "green.200";
    }

    return (
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
                <Flex justify={'center'} mt={4}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${randomUserGender}/${imageNumber}.jpg`
                        }
                        alt={'Author'}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={2}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {patient.fullName}
                        </Heading>
                        <Tag
                            size={"lg"}
                            key={"lg"}
                            borderRadius='full'
                            variant='solid'
                            bgColor={color}
                        >
                            <TagLabel>{status}</TagLabel>
                        </Tag>
                        <Heading fontSize={'l'} fontWeight={200} fontFamily={'body'}>
                            No notes to display...
                        </Heading>
                    </Stack>
                </Box>
                <Stack
                    direction={'row'}
                    justify={'center'}
                    spacing={2}
                    p={2}
                >
                    <Stack>
                        <UpdateScheduleDrawer
                            initialValues={{status, version, note, patient}}
                            scheduleId={id}
                            fetchSchedules={fetchSchedules}
                        />
                    </Stack>
                </Stack>
            </Box>
        </Center>
    );
}