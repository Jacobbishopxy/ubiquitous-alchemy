import {Form, Input, Button} from 'antd'
import {UserOutlined, LockOutlined} from '@ant-design/icons'

import './Login.less'

// TODO: get invitation ID from query
export interface RegistrationProps {
    onFinish: (value: any) => Promise<any>
    onFinishFailed: (errorInfo: any) => void
}

export const Registration = () => {
    const onFinish = (values: any) => {
        console.log('Success:', values)
    }

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo)
    }

    return (
        <Form
            name="normal-login"
            className="login-form"
            initialValues={{remember: true}}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
        >
            <Form.Item
                name="username"
                rules={[{required: true, message: 'Please input your Username!'}]}
                wrapperCol={{offset: 8, span: 8}}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
            </Form.Item>

            <Form.Item
                name="password"
                rules={[{required: true, message: 'Please input your Password!'}]}
                wrapperCol={{offset: 8, span: 8}}
            >
                <Input
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    type="password"
                    placeholder="Password"
                />
            </Form.Item>

            <Form.Item
                name="confirm"
                rules={[{required: true, message: 'Please input your Password!'}]}
                wrapperCol={{offset: 8, span: 8}}
            >
                <Input
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    type="password"
                    placeholder="Confirm"
                />
            </Form.Item>


            <Form.Item
                wrapperCol={{offset: 8, span: 8}}
            >
                <Button type="primary" htmlType="submit" className="login-form-button">
                    Register
                </Button>
            </Form.Item>
        </Form>
    )
}
