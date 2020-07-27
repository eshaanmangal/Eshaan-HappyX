import React from 'react';

export const userHasLogin = ( WrapperView ) => {
    return class UserHasLogin extends React.Component{

        onClickLogout = () => {
            window.localStorage.removeItem('username');
            window.localStorage.removeItem('Token');
            this.props.history.push('/');
        }

        componentDidMount() {
            const token = window.localStorage.getItem('Token');
            if (token == null) {
                this.props.history.push('/');
            }
        }
        
        render() {
            return(
                <WrapperView { ...this.props } onClickLogout={ this.onClickLogout } />
            );
        }

    }
}