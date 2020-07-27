import React from 'react';
import { constants } from '../modules';

const TemplateSelectView = ( payload ) => {

    const { value, onChange, ...rest} = payload;

    return(
        <select { ...rest } onChange={ ( e ) => onChange( e.currentTarget.value ) }>
            {
                constants.Template_OPTIONS.map( ( option ) => {
                    return(
                        <option
                            selected={ option.id === value }
                            key={option.id}
                            value={option.id}
                        >{ option.value }</option>
                    );
                } )
            }
        </select>
    );

}

export default TemplateSelectView;