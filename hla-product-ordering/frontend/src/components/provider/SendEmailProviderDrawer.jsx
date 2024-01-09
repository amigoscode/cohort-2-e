import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, useDisclosure
} from "@chakra-ui/react";
import SendEmailProviderForm from "./SendEmailProviderForm.jsx";

const SendEmailProviderDrawer = ({fetchProviders, initialValues, providerId}) => {
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

            > Send Email</Button>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Send Email</DrawerHeader>

                    <DrawerBody>
                        <SendEmailProviderForm
                            fetchProviders={fetchProviders}
                            initialValues={initialValues}
                            providerId={providerId}
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

export default SendEmailProviderDrawer
