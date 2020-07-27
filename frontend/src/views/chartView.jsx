import React from 'react';
import Highcharts from 'highcharts/highstock';
import HighchartsReact from 'highcharts-react-official';
import { chatData } from '../modules/chartDataObject';

const ChartView = ( payload ) => {

    return(
        <HighchartsReact highcharts={Highcharts} options={ chatData[ payload.name ]( payload )  } />
    );
};

export default ChartView;
