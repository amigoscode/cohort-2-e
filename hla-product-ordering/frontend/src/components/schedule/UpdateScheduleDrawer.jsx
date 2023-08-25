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

const UpdateScheduleDrawer = ({fetchUsers, initialValues, userId}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
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

            > Update User</Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Update User</DrawerHeader>

                    <DrawerBody>
                        <UpdateScheduleForm
                            fetchUsers={fetchUsers}
                            initialValues={initialValues}
                            userId={userId}
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
