import './App.css'

import {
  Switch,
  Route,
  Link,
  withRouter,
} from "react-router-dom"

import {Breadcrumb, Layout, Menu} from "antd"
import {Apps, breadcrumbNameMap} from "./pages"
import {Content, Footer, Header} from "antd/lib/layout/layout"

const menu = [
  {to: "/", name: "Home"},
  {to: "/apps", name: "Apps"}
]

const App = withRouter(props => {
  const {location} = props
  const pathSnippets = location.pathname.split('/').filter(i => i)
  const extraBreadcrumbItems = pathSnippets.map((_, index) => {
    const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
    return (
      <Breadcrumb.Item key={url}>
        <Link to={url}>{breadcrumbNameMap[url]}</Link>
      </Breadcrumb.Item>
    )
  })
  const breadcrumbItems = [
    <Breadcrumb.Item key="home">
      <Link to="/">Home</Link>
    </Breadcrumb.Item>,
  ].concat(extraBreadcrumbItems)

  return (
    <Layout style={{minHeight: "100vh"}}>
      <Header>
        <Menu mode="horizontal">
          {
            menu.map((i, idx) =>
              <Menu.Item key={idx}>
                <Link to={i.to}>{i.name}</Link>
              </Menu.Item>
            )
          }
        </Menu>
      </Header>

      <Content style={{padding: '0 50px'}}>
        <Breadcrumb
          style={{margin: '16px 0'}}
        >
          {breadcrumbItems}
        </Breadcrumb>

        <Switch>
          <Route path="/apps" component={Apps} />
        </Switch>
      </Content>

      <Footer style={{textAlign: 'center'}}>
        <a href="https://github.com/Jacobbishopxy/ubiquitous-alchemy">
          https://github.com/Jacobbishopxy/ubiquitous-alchemy
        </a>
      </Footer>
    </Layout>
  )
})

export default App
