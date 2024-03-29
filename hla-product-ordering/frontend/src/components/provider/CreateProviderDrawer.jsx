import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Input, useDisclosure
} from "@chakra-ui/react";
import CreateProviderForm from "../shared/CreateProviderForm.jsx";
import {useAuth} from "../context/AuthContext";

const AddIcon = () => "+";

const CreateProviderDrawer = ({fetchProviders}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {provider} = useAuth();
    return (
        <>
            <Button
                leftIcon={<AddIcon/>}
                colorScheme={"teal"}
                onClick={onOpen}>
                Create Provider
            </Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Create a provider</DrawerHeader>

                    <DrawerBody>
                        <CreateProviderForm onSuccess={fetchProviders}/>
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

export default CreateProviderDrawer
