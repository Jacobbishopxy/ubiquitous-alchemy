import ReactDOM from 'react-dom'

import {ConfigProvider} from 'antd'
import enUS from "antd/lib/locale/en_US"
import {HashRouter} from "react-router-dom"

import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'




// TODO: temporary disable StrictMode for disable DOM warning
// since some Antd components are not yet supporting this mode
ReactDOM.render(
  // <React.StrictMode>
  <HashRouter>
    <ConfigProvider locale={enUS}>
      <App />
    </ConfigProvider>
  </HashRouter>,

  // </React.StrictMode>,
  document.getElementById('root')
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
