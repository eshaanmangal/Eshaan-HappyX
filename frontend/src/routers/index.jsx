import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MailerPage from '../pages/MailerPage';
import DashboardPage from '../pages/DashboardPage';
import LoginPage from '../pages/LoginPage';
import { userHasLogin } from '../hoc/userHasLogin';

const routers = () => {
    return(
        <Router>
        <Switch>
            <Route
                path='/'
                exact={ true }
                component={ LoginPage }
            />
             <Route
                path='/dashboard'
                exact={ true }
                component={ userHasLogin( DashboardPage ) }
            />
            <Route
                path='/mailer'
                exact={true}
                component={MailerPage}
            />
            {/*<Route
                path={ '/not-found' }
                exact={true}
                component={CheckUserLogin(NotFound)}
            />
            <Route
                path=''
                exact={false}
                render={() => {
                    return (
                    <Redirect to={ '/not-found' } />
                    );
                }}
            /> */}
        </Switch>
    </Router>
    );
}

export default routers;