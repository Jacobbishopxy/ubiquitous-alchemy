import './App.css'

import {
  Switch,
  Route,
  Link,
  withRouter,
} from "react-router-dom"

import {Breadcrumb, } from "antd"
import {Apps} from "./pages"



const breadcrumbNameMap: Record<string, string> = {
  '/apps': 'Application List',
  '/apps/datalab': 'Data Lab',
}

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
    <div className="App">
      <header className="App-header">
        Welcome
      </header>

      <div className="App-body" >
        <Breadcrumb>{breadcrumbItems}</Breadcrumb>

        <Link to="/">Home</Link>
        <Link to="/apps">Application List</Link>

        <Switch>
          <Route path="/apps" component={Apps} />
        </Switch>

      </div>

      <div className="App-footer">
        <a
          className="App-link"
          href="https://github.com/Jacobbishopxy/ubiquitous-alchemy"
        >
          https://github.com/Jacobbishopxy/ubiquitous-alchemy
        </a>
      </div>


    </div>
  )
})

export default App
