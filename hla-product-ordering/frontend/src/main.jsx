import React from 'react'
import ReactDOM from 'react-dom/client'
import User from './User.jsx'
import Provider from './Provider.jsx'
import './index.css'
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/react'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./components/login/Login.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import Signup from "./components/signup/Signup.jsx";
import Home from "./Home.jsx";
import Patient from "./Patient.jsx";

const { ToastContainer} = createStandaloneToast()
const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/signup",
        element: <Signup/>
    },
    {
        path: "dashboard",
        element: <ProtectedRoute><Home/></ProtectedRoute>
    },
    {
        path: "dashboard/users",
        element: <ProtectedRoute><User/></ProtectedRoute>
    },
    {
        path: "dashboard/providers",
        element: <ProtectedRoute><Provider/></ProtectedRoute>
    },
    {
        path: "dashboard/patients",
        element: <ProtectedRoute><Patient/></ProtectedRoute>
    }
])

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router}/>
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )