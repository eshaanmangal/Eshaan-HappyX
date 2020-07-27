import React from 'react';
import HeaderLogo from '../images/logo_header.png';
import Avatar from '../images/avatar.png';
import { OpenCloseHoc  } from '../hoc/openCloseHoc';

const headerView = ( payload ) => {

    const { onClickLogout } = payload;
    const userName = window.localStorage.getItem('username');

    return(
        <header className='header'>
            <div className='header__logo'><img src={ HeaderLogo } alt='Xebia HappyX' /></div>
            <OpenCloseHoc render={ ( props ) => {
                const { isOpen, onHandleClick, ref } = props;
                return(
                    <div className='header__userContainer' ref={ ref } onClick={ onHandleClick }>
                        <div className='userContainer__info'>
                            <img src={ Avatar } alt='Avatar' />
                            <span>{ userName }</span>
                        </div>
                        { ! isOpen ? null :
                            <ul className='userContainer__dropdown'>
                                <li className='userContainer__dropdown__item'>Profile</li>
                                <li className='userContainer__dropdown__item' onClick={ onClickLogout }>Logout</li>
                            </ul>
                        }
                    </div>
                );

            } } />
        </header>
    );
};

export default headerView;