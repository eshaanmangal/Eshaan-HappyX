import React from 'react';
import { isEmpty, toString } from 'lodash';
import BgImageLogin from '../images/bg_login_page.png';
import Logo from '../images/logo_login_page.svg';
import LogoFooter from '../images/logo_login_footer.png';
import { preloaderView as PreloaderView } from '../views/preloaderView';

export const loginPageView = ( payload ) => {

    const { username, password, isValid, onInputChange, onFormSubmit, isLoading } = payload;

    const isDisabledButton = ( isEmpty( toString( username ) ) || isEmpty( toString( password ) ) );

    return(
        <div className='loginPage' style={ { backgroundImage: `url(${BgImageLogin})` } }>
            <form onSubmit={ onFormSubmit } className='loginPage__container'>
                <img src={ Logo } alt='Xebia' />
                <h2>HappyX Login</h2>
                { isValid ? null : <div className='loginPage__error'>Please Enter Correct UserName and Password.</div> }
                <input className='loginPage__inputField' value={ username } type='text' onChange={ ( e ) => onInputChange( { username: e.currentTarget.value } ) } />
                <input className='loginPage__inputField' value={ password } type='password' onChange={ ( e ) => onInputChange( { password: e.currentTarget.value } ) } />
                <button type='submit' className='loginPage__button' disabled={ isDisabledButton }>
                    { isLoading ? <PreloaderView /> : 'Login' }
                </button>
            </form>
            <div className='loginPage__footer'>
                <img src={ LogoFooter } alt='xebia' />
                <p>Xebia Group © 2020. All rights reserved.</p>
            </div>
        </div>
    );

};