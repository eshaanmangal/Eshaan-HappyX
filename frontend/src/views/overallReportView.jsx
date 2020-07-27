import React, { useState, useEffect } from 'react';
import ChartView from './chartView';
import DownloadReport from './downloadReport';
import { constants, fetch  } from '../modules';

const OverallReportView = () => {

    const [ defaultState, updateDefaultState  ] = useState( { 
        totalCount: 0,
        chartData: []
    } );

    useEffect( () => {

        fetch.get( { url: constants.SERVICE_URLS.CHART, callbackHandler: ( response ) => {

                const { status, payload } = response;
                if( status === constants.SUCCESS ) {
                    
                    const { moodStatistics, totalCount } = payload;
                    const result = [];
                    for( var i=0; i<moodStatistics.length; i++ ){
                        const { moodType, count } = moodStatistics[ i ];
                        result.push( [ moodType, count ] );
                    }

                    updateDefaultState( ( state ) => {
                        return {
                            ...state,
                            totalCount,
                            chartData: result
                        };
                    } );

                }
            }

        } );

    }, [] );

    return(
        <div className='commonbx reports'>
            <div className='commonbx__head'>
                <h3 className='commonbx__heading'>Overall Report</h3>
                <DownloadReport />
            </div>
            <div className='commonbx__body'>
                { defaultState.chartData.length === 0 ? 'Loading...' : <ChartView { ...defaultState } name='getPieChart' /> }
            </div>
        </div>
    );

}

export default OverallReportView;