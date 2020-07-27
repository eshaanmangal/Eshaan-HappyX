import React from 'react';
import HeaderView from '../views/headerView';
import OverallReportView from '../views/overallReportView';
import CustomDateReportView from '../views/customDateReportView';
import SendEmailComponent from '../components/SendEmailComponent';

const DashboardPage = ( props ) => {

    const { onClickLogout } = props;

    return(
        <>
            <HeaderView onClickLogout={ onClickLogout } />
            <div className='dashborad clearfix'>
                <h2 className='pageTitle'>Dashboard</h2>
                <div className='dashborad__wrapper'>
                    <OverallReportView />
                    <CustomDateReportView />
                    <SendEmailComponent />
                </div>
            </div>
        </>
    );

}

export default DashboardPage;
