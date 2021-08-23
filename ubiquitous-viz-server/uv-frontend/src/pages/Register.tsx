import * as auth from "../services/auth"
import { Registration } from "../components/Login"

// TODO: API from services
export const RegisterPage = () => {
    const onRegister = async (value: API.Registration) => {
        return auth.sendInvitation(value)
    }
    const onRegisterFailed = (errorInfo: any) => {
        // console.log('Failed:', errorInfo)
    }
    return <Registration onFinish={onRegister}
        onFinishFailed={onRegisterFailed}
    />
}
