import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Input, useDisclosure
} from "@chakra-ui/react";
import CreateUserForm from "../shared/CreateUserForm.jsx";

const AddIcon = () => "+";

const CreateUserDrawer = ({fetchUsers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return (
        <>
            <Button
                leftIcon={<AddIcon/>}
                colorScheme={"teal"}
                onClick={onOpen}>
                Create User
            </Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Create your account</DrawerHeader>

                    <DrawerBody>
                        <CreateUserForm onSuccess={fetchUsers}/>
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

export default CreateUserDrawer
