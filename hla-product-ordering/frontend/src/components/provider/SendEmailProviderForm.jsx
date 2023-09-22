import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select,Stack} from "@chakra-ui/react";
import {sendEmail} from "../../services/email.js";
import {successNotification, errorNotification} from "../../services/notification.js";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <textarea
                {...field}
                {...props}
                style={{ 
                    height: '200px', 
                    width: '680px', // Adjust the height and width as needed
                    border: '2px solid blue', // Add blue border style here
                }}              
            />
        </Box>
    );
};
// And now we can use these
const SendEmailProviderForm = ({ fetchProviders, initialValues, providerId }) => {
    
    let emailInfo = initialValues.email;
    console.log(initialValues)
    console.log(emailInfo)
    return (
        <>
            <Formik
                initialValues={{
                    providerId: 0,
                    content: ''
                }}
                
                onSubmit={(emailToSend, {setSubmitting}) => {
                
                    setSubmitting(true);
                    emailToSend.providerId = providerId;
                    sendEmail(emailToSend)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Email Sent"
                            )
                            fetchProviders();
                        }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"120px"}>
                            <MyTextInput
                                label="Content"
                                name="content"
                                type="text"
                                placeholder="Type something here..."
                            />


                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Send</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default SendEmailProviderForm;