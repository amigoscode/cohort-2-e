import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, useDisclosure
} from "@chakra-ui/react";
import UpdateScheduleForm from "./UpdateScheduleForm.jsx";

const UpdateScheduleDrawer = ({fetchSchedules, initialValues, scheduleId}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return (
        <>
            <Button
                mt={10}
                w={"full"}
                bg={'gray.200'}
                color={'black'}
                rounded={"xl"}
              /*  boxShadow={'0 5px 20px 0px rgb(72 187 120 / 43%)'}*/
                _hover={{
                    bg:'gray.300'
                }}
                _focus={{
                    bg:'gray.300'
                }}

                onClick={onOpen}

            > Update Schedule</Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Update Schedule</DrawerHeader>

                    <DrawerBody>
                        <UpdateScheduleForm
                            fetchSchedules={fetchSchedules}
                            initialValues={initialValues}
                            scheduleId={scheduleId}
                        />
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

export default UpdateScheduleDrawer
