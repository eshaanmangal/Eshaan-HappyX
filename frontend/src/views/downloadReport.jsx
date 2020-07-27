import React from 'react';
import { constants  } from '../modules';
import axios from 'axios';
import DownloadIcon from '../images/icon_download.svg';

const DownloadReport = () => {

    return(
        <img className='commonbx__download' src={ DownloadIcon } onClick={ downloadFile } alt='download'  />
    );

}

export default DownloadReport;

const downloadFile = () => {

    axios({
        url: constants.SERVICE_URLS.DOWNLOAD_FILE,
        method: 'GET',
        responseType: 'blob', // important
      }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data] ));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'DownloadFile.csv');
        document.body.appendChild(link);
        link.click();
        window.URL.revokeObjectURL(url);
      });
}