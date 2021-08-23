import './App.less'

import {
	Switch,
	Route,
	Link,
	withRouter,
	RouteComponentProps,
} from "react-router-dom"

import { Breadcrumb, Card, Col, Layout, Menu, Row } from "antd"
import { Content, Footer, Header } from "antd/lib/layout/layout"

import { Apps, breadcrumbNameMap, Home, LoginPage, RegisterPage } from "./pages"
import { OnInvitation } from './pages/OnInvitation'
import { useState } from 'react'
import { LogoutPage } from './pages/LogoutPage'

const menu = [
	{ to: "/", name: "Home" },
	{ to: "/apps", name: "Apps" }
]

interface AppHeaderProps {
	isLogin: boolean
}
const AppHeader = (props: AppHeaderProps) => {
	return (
		<Header>
			<Row>
				<Col span={20}>
					<Menu mode="horizontal">
						{
							menu.map((i, idx) =>
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

const footerUrl = "https://github.com/Jacobbishopxy/ubiquitous-alchemy"

const AppFooter = () => {
	return <Footer><a href={footerUrl}>{footerUrl}</a></Footer>
}

interface AppSwitchProps {
	setIsLogin: React.Dispatch<React.SetStateAction<boolean>>
}
const AppSwitch = (props: AppSwitchProps) => {
	return (
		<Switch>
			<Route path="/" exact component={Home} />
			<Route path="/apps" component={Apps} />
			<Route path="/login">
				<LoginPage setLogined={props.setIsLogin} />
			</Route>
			<Route path="/registration" component={RegisterPage} />
			<Route path="/register" component={OnInvitation} />
			<Route path="/logout" >
				<LogoutPage setLogined={props.setIsLogin} />
			</Route>
		</Switch>
	)
}


interface AppProps extends RouteComponentProps<any> { }

const getBreadcrumbItems = (props: AppProps) => {
	const pathSnippets = props.location.pathname.split('/').filter(i => i)
	const extraBreadcrumbItems = pathSnippets.map((_, index) => {
		const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
		return (
			<Breadcrumb.Item key={url}>
				<Link to={url}>{breadcrumbNameMap[url]}</Link>
			</Breadcrumb.Item>
		)
	})
	return [
		<Breadcrumb.Item key="home">
			<Link to="/">Home</Link>
		</Breadcrumb.Item>,
	].concat(extraBreadcrumbItems)
}

const App = withRouter(props => {
	const breadcrumbItems = getBreadcrumbItems(props)
	const [isLogin, setIsLogin] = useState(false)
	return (
		<Layout >
			<AppHeader isLogin={isLogin} />
			<Content>
				<Breadcrumb>{breadcrumbItems}</Breadcrumb>
				<Card><AppSwitch setIsLogin={setIsLogin} /></Card>
			</Content>
			<AppFooter />
		</Layout>
	)
})

export default App
