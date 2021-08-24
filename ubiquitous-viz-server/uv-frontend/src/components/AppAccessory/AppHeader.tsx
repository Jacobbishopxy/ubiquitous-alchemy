
import {Link} from "react-router-dom"
import {Col, Menu, Row} from "antd"
import {Header} from "antd/lib/layout/layout"

interface MenuObject {
    to: string,
    name: string
}

interface AppHeaderProps {
    isLogin: boolean
    menu: MenuObject[]
}

export const AppHeader = (props: AppHeaderProps) => {
    return (
        <Header>
            <Row>
                <Col span={20}>
                    <Menu mode="horizontal">
                        {
                            props.menu.map((i, idx) =>
                                <Menu.Item key={idx}>
                                    <Link to={i.to}>{i.name}</Link>
                                </Menu.Item>
                            )
                        }
                    </Menu>
                </Col>

                <Col offset={2}>
                    {props.isLogin ? <Link to="/logout">Logout</Link> : <Link to="/login">Login</Link>}
                </Col>
            </Row>
        </Header>
    )
}