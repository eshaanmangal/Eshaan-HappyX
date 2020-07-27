import { constants } from './index'; 
export const utils = {

    chartCategory( value ) {
        var _newValue = new Date( value );
        var _dd = String(_newValue.getDate()).padStart(2, '0');
        return `${_dd} ${constants.MONTHS[ _newValue.getMonth() ].slice(0,3)}`;
    },
    dateFormate( value ) {
        var _newValue = new Date( value );
        var _dd = String(_newValue.getDate()).padStart(2, '0');
        var _mm = String(_newValue.getMonth() + 1 ).padStart(2, '0');
        var _yy = String(_newValue.getFullYear());
        return `${_yy}-${_mm}-${_dd}`;

    }

};