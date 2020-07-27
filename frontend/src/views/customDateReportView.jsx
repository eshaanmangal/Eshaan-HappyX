import React, { useState } from 'react';
import ChartView from './chartView';
import DownloadReport from './downloadReport';
import { constants, fetch, utils } from '../modules';
import TemplateSelectView from './templateSelectView';
import { forEach } from 'lodash';
import DateRangePicker from '@wojtekmaj/react-daterange-picker';

const CustomDateReportView = () => {
    const [ state, updateState  ] = useState( {
        seriesData: [],
        categoriesData: [],
        templateID: '2',
        dateValue: [ new Date(), new Date() ]
    } );

     const onFilterChange = ( payload ) => {
        const { templateID, dateValue } = { ...state, ...payload };
        const startDate = utils.dateFormate( dateValue[ 0 ] );
        const endDate = utils.dateFormate( dateValue[ 1 ] );

        fetch.get( {
            url: constants.SERVICE_URLS.CHART_BY_DATE,
            requestParams: {
                templateID,
                startDate,
                endDate
            },
            callbackHandler: ( response ) => {

                const { status, payload } = response;
                if( status === constants.SUCCESS ) {
                    
                    var result = [];
                    var xData = [];
                    var count = 0;

                    forEach( payload, ( item, key ) => {

                        if( count === 0 ) {

                            result = item.map( ( j ) => {
                                var _sub = Object.keys( j )[ 0 ];
                                return {
                                    name: _sub,
                                    data: [ j[ _sub ]  ]
                                }
                            } );

                        } else {
                            item.forEach( ( j ) => {
                                var _sub = Object.keys( j )[ 0 ];
                                const index = result.findIndex( ( i ) => i.name === _sub );
                                if( -1 !== index ) {
                                    result[ index ].data.push( j[ _sub ]  );
                                }
                               
                            } );
                        }

                        xData.push( utils.chartCategory( key ) );
                        
                        count++;

                    } );

                    updateState( ( _s ) => ({ ..._s, seriesData: result, categoriesData: xData }) );

                }
            }

        } );
     }


    return(
        <div className='commonbx reports'>
            <div className='commonbx__head'>
                <h3 className='commonbx__heading'>Custom Date Report</h3>
                <TemplateSelectView
                    className='commonbx__select'
                    value={state.templateID}
                    onChange={ ( value ) => {
                        updateState( ( _s ) => ({ ..._s, templateID: value }) );
                        onFilterChange({ templateID: value});
                    } }
                />
                <div className='commonbx__calender'>
                <DateRangePicker
                    value={ state.dateValue }
                    maxDate={ new Date() }
                    onChange={ (value) => {
                        updateState( ( _s ) => ({ ..._s, dateValue: value }) );
                        onFilterChange( {dateValue: value} );
                    } }
                />
                </div>
                <DownloadReport />
            </div>
            <div className='commonbx__body'>
                <ChartView {...state} name='getBarChart' />
            </div>
        </div>
    );
};

export default CustomDateReportView;