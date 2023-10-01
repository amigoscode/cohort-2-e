import {Field, Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {
    Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack, Accordion,
    AccordionItem,
    AccordionButton,
    AccordionPanel,
    AccordionIcon, Textarea
} from "@chakra-ui/react";
import {updateSchedule} from "../../services/schedule";
import {successNotification, errorNotification} from "../../services/notification.js";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};
const MyNumberInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input type={"number"} className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MyTextAreaInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Textarea  className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MyDateInput = ({ label, ...props }) => {
    const [field, meta, helpers] = useField(props);

    const handleChange = (event) => {
        // Set the field value when the date picker value changes
        helpers.setValue(event.target.value);
    };

    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input
                type="date"
                className="text-input"
                {...field}
                {...props}
                onChange={handleChange} // Add this onChange handler
            />
            {meta.touched && meta.error ? (
                <Alert className="error" status="error" mt={2}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};
  

const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};
// And now we can use these
const UpdateScheduleForm = ({fetchSchedules, initialValues, scheduleId}) => {
    return (
        <>
            <Formik
                initialValues={{
                    ...initialValues,
                    patient: {
                        ...initialValues.patient,
                        dob: initialValues.patient?.dob
                            ? initialValues.patient.dob.substring(0, 10) // Format the date
                            : '', // Set the initial date value here
                    },
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(30, 'Must be 15 characters or less')
                        .required('Required'),
                })}

              /*  onSubmit={(updatedSchedule, {setSubmitting}) => {
                    setSubmitting(true);
                    updatedSchedule.id = scheduleId;
                    updatedSchedule.patientId = initialValues.patient.id;
                    updateSchedule(scheduleId, updatedSchedule)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "User updated",
                                `${updateSchedule} was successfully updated`
                            )
                            fetchSchedules();
                        }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}*/

                onSubmit={(values, { setSubmitting }) => {
                    setSubmitting(true);
                    // Helper function to format date to ISO 8601 format
                    const formatDateToISO = (dateString) => {
                        const date = new Date(dateString);
                        return date.toISOString();
                    };

                    // Construct the updatedSchedule object
                    const updatedSchedule = {
                        status: values.status,
                        version: {
                            id: null,
                            startDate: values.version.startDate ? formatDateToISO(values.version.startDate) : null,
                            endDate: values.version.endDate ? formatDateToISO(values.version.endDate) : null,
                            quantity: values.version.quantity,
                            schedulePeriod: values.version.schedulePeriod,
                            version: null,
                            scheduleId: scheduleId, // Adjust if needed
                            updatedBy: null,
                            updatedAt: null,
                        },
                        note: {
                            note: values.note.note,
                            id: null,
                            scheduleId: scheduleId,
                            scheduleVersion: null,
                            createdAt: null,
                            createdBy: null,
                        },
                        patient: {
                            id: initialValues.patient.id,
                            fullName: values.patient.fullName,
                            gender: values.patient.gender,
                            mrn: values.patient.mrn,
                            dob: formatDateToISO(values.patient.dob),
                            createdAt: null,
                        },
                        id: scheduleId,
                        patientId: initialValues.patient.id,
                    };

                    // Make the PUT request
                    updateSchedule(scheduleId, updatedSchedule)
                        .then((res) => {
                            console.log(res);
                            successNotification("Schedule updated", "Schedule was successfully updated");
                            fetchSchedules();
                        })
                        .catch((err) => {
                            console.log(err);
                            errorNotification(err.code, err.response.data.message);
                        })
                        .finally(() => {
                            setSubmitting(false);
                        });
                }}

            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Accordion defaultIndex={[0]} allowMultiple>
                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            Patient Details Section
                                        </Box>
                                        <AccordionIcon />
                                    </AccordionButton>
                                </h2>
                                <AccordionPanel pb={4}>
                                    <Stack spacing={"24px"}>
                                        <MyTextInput
                                            label="Name"
                                            name="patient.fullName"
                                            type="text"
                                            placeholder=""
                                        />
                                        <MySelect label="Gender" name="patient.gender">
                                            <option value="">Select gender</option>
                                            <option value="MALE">MALE</option>
                                            <option value="FEMALE">FEMALE</option>
                                        </MySelect>

                                        <MyDateInput
                                            label="DOB"
                                            name="patient.dob"
                                            placeholder="Pick a dob"
                                        />
                                        <MyTextInput
                                            label="MRN"
                                            name="patient.mrn"
                                            type="text"
                                            placeholder="Pick a mrn"
                                        />

                                    </Stack>
                                </AccordionPanel>
                            </AccordionItem>

                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            Schedule Details Section
                                        </Box>
                                        <AccordionIcon />
                                    </AccordionButton>
                                </h2>
                                <AccordionPanel pb={4}>
                                    <Stack spacing={"24px"}>
                                        <MyDateInput
                                            label="Start date"
                                            name="version.startDate"
                                            placeholder="Pick a start date"
                                        />
                                        <MyDateInput
                                            label="End date"
                                            name="version.endDate"
                                            placeholder="Pick an end date"
                                        />
                                        <MyNumberInput
                                            label="Quantity"
                                            name="version.quantity"
                                            type="number"
                                            placeholder="Pick the quantity"
                                        />
                                        <MyNumberInput
                                            label="Period"
                                            name="version.schedulePeriod"
                                            type="number"
                                            placeholder="Pick the scheduled period"
                                        />
                                        <MySelect label="Status" name="status">
                                            <option value="">Select Status</option>
                                            <option value="REVIEW">REVIEW</option>
                                            <option value="REVIEWED">REVIEWED</option>
                                            <option value="REVIEWED">APPROVED_AND_EMAIL_SENT</option>
                                        </MySelect>
                                    </Stack>
                                </AccordionPanel>
                            </AccordionItem>
                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            Comment Section
                                        </Box>
                                        <AccordionIcon />
                                    </AccordionButton>
                                </h2>
                                <AccordionPanel pb={4}>
                                    <MyTextAreaInput
                                        label="Notes"
                                        name="note.note"
                                        placeholder="Write comments"
                                        size="xl"/>
                                </AccordionPanel>
                            </AccordionItem>
                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            Generated Email Section
                                        </Box>
                                        <AccordionIcon />
                                    </AccordionButton>
                                </h2>
                                <AccordionPanel pb={4}>
                                    <MyTextAreaInput
                                        label="Generated Email"
                                        name="note.note"
                                        placeholder="Write comments"
                                        size="xl"/>
                                </AccordionPanel>
                            </AccordionItem>
                        </Accordion>
                        <br/>
                        <br/>
                        <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdateScheduleForm;