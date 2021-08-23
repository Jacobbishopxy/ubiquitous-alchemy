import { Login } from "../components"
import * as auth from "../services/auth"
// TODO: API from services
export const LoginPage = () => {

    const onFinish = async (value: API.Login) => {
        return auth.login(value)
    }
    const onFinishFailed = (errorInfo: any) => {
        // console.log('Failed:', errorInfo)
    }
    const registrationHref = "#/registration"
    const forgetPasswordHref = "/"
    return <Login
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        registrationHref={registrationHref}
        forgetPasswordHref={forgetPasswordHref}
    />
}
