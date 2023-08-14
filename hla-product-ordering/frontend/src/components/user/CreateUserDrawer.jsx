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
import {useAuth} from "../context/AuthContext";

const AddIcon = () => "+";

const CreateUserDrawer = ({fetchUsers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {user} = useAuth();
    const isAdmin = user != null && user.roles[0] === "ADMIN";
    return (
        <>
            <Button
                leftIcon={<AddIcon/>}
                colorScheme={"teal"}
                isDisabled={isAdmin}
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
