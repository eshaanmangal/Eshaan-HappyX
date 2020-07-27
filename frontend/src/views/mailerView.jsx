import React from 'react';
import HeaderLogo from '../images/logo_header.png';

const moodType = {
    confident:'30pykuIQHxzQsfefte',
    upset:'pDdzX4l9jqA80',
    anxious:'9Y5BbDSkSTiY8',
    confused:'l4JA1COQqiZB6',
    highlymotivated:'CjmvTCZf2U3p09Cn0h',
    motivated:'xT9DPnxOqFNu0vObyU',
    nochange:'xT5LMEcHRXKXpIHCCI',
    clueless:'11ljFmrpYVYxTW',
    accomplished:'CjmvTCZf2U3p09Cn0h',
    excited:'xT9DPnxOqFNu0vObyU',
    stressed:'xT5LMEcHRXKXpIHCCI',
    exhausted:'11ljFmrpYVYxTW'
}


export const mailerView = ( payload ) => {

    const { mood } = payload;

    return(
        <div className='mailerPage'>
            <div className='mailerPage__logo'><img src={ HeaderLogo } alt='Xebia HappyX' /></div>
            <div className='mailerPage__image'>
                <img src={`https://i.giphy.com/media/${moodType[ mood ]}/giphy.gif`} alt='gif' />
            </div>
        </div>
    );
};