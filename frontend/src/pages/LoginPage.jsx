import React from 'react';
import { cloneDeep } from 'lodash';
import { constants  } from '../modules/constants';
import { fetch } from '../modules/httpServices';
import { loginPageView as LoginPageView } from '../views/loginPageView';

export default class LoginPage extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            isLoading: false,
            message: '',
            username: '',
            password: '',
            isValid: true
        };
    }

    componentDidMount() {
        this.redirectDashboardPage();
    }

    onInputChange = ( payload  ) => {
        this.setState( { ...this.state, ...payload, isValid: true } );
    }

    onFormSubmit = ( e ) => {
        e.preventDefault();
        this.setState( { isLoading: true }, this.validateUserInfo )
    }

    validateUserInfo = () => {
        const { username, password } = this.state;
        fetch.post( { url: constants.SERVICE_URLS.LOGIN, requestBody: { username, password }, callbackHandler: ( response ) => {

                const { status, message, payload } = response;
                const _state = cloneDeep( this.state );
                _state.isLoading = false;

                if( status === constants.SUCCESS ) {
                    _state.message = '';
                    _state.isValid = true;
                    window.localStorage.setItem('Token', payload.token );
                    window.localStorage.setItem('username', username );
                } else {
                    _state.message = message;
                    _state.isValid = false;
                    window.localStorage.removeItem('username');
                    window.localStorage.removeItem('Token');
                }

                this.setState( _state, this.redirectDashboardPage );

            }
        } );
    };

    redirectDashboardPage = () => {
        const token = window.localStorage.getItem('Token');
        if (token != null) {
            this.props.history.push('/dashboard');
        }
    }

    render() {

        return(
            <LoginPageView { ...{
                ...this.state,
                onInputChange: this.onInputChange,
                onFormSubmit: this.onFormSubmit
            } } />
        );
    }
}