import {Text} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/sidebar.jsx";


const Home = () => {
    return (
        <SidebarWithHeader>
            <Text fontSize={"6xl"}>Dashboard</Text>
        </SidebarWithHeader>
    )


}
export default Home;