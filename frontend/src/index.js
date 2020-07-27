import React from 'react';
import ReactDOM from 'react-dom';
import './scss/index.scss';
import Routers from './routers/index';
import * as serviceWorker from './serviceWorker';
ReactDOM.render(<Routers />, document.getElementById('root'));
serviceWorker.unregister();
