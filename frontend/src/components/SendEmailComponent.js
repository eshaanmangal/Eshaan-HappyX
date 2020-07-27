import React from 'react';
import SendEmailView from '../views/sendEmailView';
import { constants  } from '../modules/constants';
import { fetch } from '../modules/httpServices';
import { cloneDeep, lowerCase } from 'lodash';


export default class SendEmailComponent extends React.Component{

    constructor( props ) {
        super( props );
        this.state = {
            isLoading: true,
            deptLocText: '',
            searchText: '',
            message: '',
            userList: [],
            selectedEmail: [],
            deptLocList: [],
            selectedValue: [],
            selectedDeptLoc: [],
            messageText:'',
            templateId:'1',
            subjectText:''
        };
    }

    componentDidMount() {
        this.getUserList();
        this.getDeptLocList();
    }

    getUserList = () => {
        fetch.get( { url: constants.SERVICE_URLS.GET_UERS_LIST, callbackHandler: ( response ) => {

                const { status, message, payload } = response;
                const _state = cloneDeep( this.state );
                _state.isLoading = false;

                if( status === constants.SUCCESS ) {
                    _state.message = '';
                    _state.userList = payload;
                } else {
                    _state.message = message;
                }

                this.setState( _state );
            }
            
        } );
    }

    getDeptLocList = () => {
        fetch.get({ url: constants.SERVICE_URLS.GET_DEPT_LOC_LIST, callbackHandler: ( response ) => {

                const { status, message, payload } = response;
                const _state = cloneDeep( this.state );
                _state.isLoading = false;

                if( status === constants.SUCCESS ) {
                    _state.message = '';
                    _state.deptLocList = payload;
                } else {
                    _state.message = message;
                }

                this.setState( _state );
            }
            
        });
        
    }

    onEmailClick = ( email ) => {
        const _state = cloneDeep( this.state );
        _state.selectedEmail.push( email );
        _state.searchText = '';
        this.setState( _state );
    }

    onValueClick = ( value ) => {
        const _state = cloneDeep( this.state );

        for(var i=0;i<_state.userList.length;i++) {
            if(value === _state.userList[i].departmentName || value === _state.userList[i].location) {
                _state.selectedValue.push( _state.userList[i].staffEmailId );
                _state.selectedDeptLoc.push( value );
            }

        }

        _state.deptLocText = '';
        this.setState( _state );

    }

    onSearchChange = ( payload ) => {
        this.setState( { ...this.state, ...payload} );
    }

    onRemoveEmail = ( index ) => {
        const _state = cloneDeep( this.state );
        _state.searchText = '';
        _state.selectedEmail.splice( index, 1 );
        this.setState( _state );

    };

    onRemoveValue = ( value ) => {
        const _state = cloneDeep( this.state );
        _state.deptLocText = '';
        _state.selectedValue.splice( value, 1 );
        this.setState( _state );

    };
    
    filterUserList = () => {
        const { searchText, userList, selectedEmail } = this.state;

        const removeSelectedUser = userList.filter( ( user ) => {
            return ! selectedEmail.includes( user.staffEmailId );
        } );

        return searchText.length === 0 ? [] : removeSelectedUser.filter( ( user ) => {
            return lowerCase( user.staffEmailId ).includes( lowerCase( searchText ) );
        } );
    }

    filterDeptLocList = () => {
        const { deptLocText, deptLocList, selectedDeptLoc } = this.state;
        const removeSelectedValue = deptLocList.filter( (value) => {
            return ! selectedDeptLoc.includes( value );
        } );

        return deptLocText.length === 0 ? [] : removeSelectedValue.filter( ( value ) => {
            return lowerCase( value ).includes( lowerCase( deptLocText ));
        } )
    }

    getEmailParams = () => {

        let result = [];
        const { selectedEmail, selectedValue } = this.state;

        for( var i=0; i<selectedEmail.length; i++ ) {
            result.push(selectedEmail[i]);
        }
 
        for( i=0; i<selectedValue.length; i++) {
            result.push(selectedValue[i]);
        }

        return result;
    }

    

    onSendEmailClick = () => {
        this.setState( { isLoading: true }, () => {

            const { templateId, messageText, subjectText} = this.state;

            fetch.post( {
                url: constants.SERVICE_URLS.SEND_EMAIL,
                requestBody: {
                    emails: this.getEmailParams(),
                    templateId,
                    message: messageText,
                    subject: subjectText
                },
                callbackHandler: (response) => {
                
                const { status, message } = response;
                const _state = cloneDeep( this.state );
                _state.isLoading = false;
                _state.selectedEmail = [];
                _state.selectedValue = [];
                _state.selectedDeptLoc = [];
                _state.messageText = '';
                _state.templateId = '1';
                _state.subjectText = '';
                
                if( status === constants.SUCCESS ) {
                    _state.message = '';
                    alert("Email has been sent!");
                } else {
                    _state.message = message;
                    alert("There's some error. The email was not sent!")
                }

                this.setState( _state );

                }

             } )
        } )
    }

    render() {

        const { deptLocText, searchText, selectedEmail, isLoading, selectedValue,messageText,templateId,subjectText } = this.state;

        return(
            <SendEmailView
                { ...{
                    isLoading,
                    onSearchChange: this.onSearchChange,
                    onEmailClick: this.onEmailClick,
                    onValueClick: this.onValueClick,
                    onRemoveEmail: this.onRemoveEmail,
                    onRemoveValue: this.onRemoveValue,
                    onSendEmailClick: this.onSendEmailClick,
                    selectedEmail,
                    selectedValue,
                    deptLocText,
                    searchText,
                    filterData: this.filterUserList(),
                    filterDeptLocData: this.filterDeptLocList(),
                    messageText,
                    templateId,
                    subjectText
                } }
            />
        );
    }

}