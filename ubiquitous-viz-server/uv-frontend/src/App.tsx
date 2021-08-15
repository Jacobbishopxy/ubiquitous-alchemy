import './App.less'

import {
	Switch,
	Route,
	Link,
	withRouter,
	RouteComponentProps,
} from "react-router-dom"

import {Breadcrumb, Card, Col, Layout, Menu, Row} from "antd"
import {Content, Footer, Header} from "antd/lib/layout/layout"

import {Apps, breadcrumbNameMap, Home, LoginPage, RegisterPage} from "./pages"

const menu = [
	{to: "/", name: "Home"},
	{to: "/apps", name: "Apps"}
]

const AppHeader = () => {
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
					<Link to="/login">Login</Link>
				</Col>
			</Row>
		</Header>
	)
}

const footerUrl = "https://github.com/Jacobbishopxy/ubiquitous-alchemy"

const AppFooter = () => {
	return <Footer><a href={footerUrl}>{footerUrl}</a></Footer>
}

const AppSwitch = () => {
	return (
		<Switch>
			<Route path="/" exact component={Home} />
			<Route path="/apps" component={Apps} />
			<Route path="/login" component={LoginPage} />
			<Route path="/registration" component={RegisterPage} />
		</Switch>
	)
}


interface AppProps extends RouteComponentProps<any> {}

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

	return (
		<Layout >
			<AppHeader />
			<Content>
				<Breadcrumb>{breadcrumbItems}</Breadcrumb>
				<Card><AppSwitch /></Card>
			</Content>
			<AppFooter />
		</Layout>
	)
})

export default App
