import React from 'react';

export const preloaderView = () => {

    return (
        <svg width='20px' height='20px' viewBox="0 0 100 100">
            <path fill="#fff" d="M31.6,3.5C5.9,13.6-6.6,42.7,3.5,68.4c10.1,25.7,39.2,38.3,64.9,28.1l-3.1-7.9c-21.3,8.4-45.4-2-53.8-23.3
  c-8.4-21.3,2-45.4,23.3-53.8L31.6,3.5z" transform="rotate(141.993 50 50)">
                <animateTransform attributeName="transform" attributeType="XML" type="rotate" dur=".5s" from="0 50 50" to="360 50 50" repeatCount="indefinite"></animateTransform>
            </path>
        </svg>
    );

}