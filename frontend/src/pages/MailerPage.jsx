import React from 'react';
import { mailerView as MailerView } from '../views/mailerView';
import { constants  } from '../modules/constants';
import { fetch } from '../modules/httpServices';
import { isEmpty, lowerCase, cloneDeep, upperCase } from 'lodash';

const moods = [ 'confident', 'upset', 'anxious', 'confused', 'highlymotivated', 'motivated', 'nochange', 'clueless', 'excited', 'stressed', 'accomplished', 'exhausted'];

export default class MailerPage extends React.Component{

    constructor( props ) {
        super( props );
        const { moodType } = this.getQuery();
        this.state = {
            isLoading: true,
            message: '',
            mood: moods.includes( lowerCase( moodType ) ) ? lowerCase( moodType ) : 'confident'
        };
    }

    componentDidMount() {

        const { email, moodType } = this.getQuery();
        if( ! isEmpty( email ) || ! isEmpty( moodType ) ) {
            if( moods.includes( lowerCase( moodType ) ) ) {
                this.updateData();
            }
        }
    }

    updateData = () => {

        const { email, moodType, templateId } = this.getQuery();
        fetch.get( { url: constants.SERVICE_URLS.UPDATE_MOOD, requestParams:{ email, moodType: upperCase( moodType ), templateId },callbackHandler: ( response ) => {

                const { status, message } = response;
                const _state = cloneDeep( this.state );
                _state.isLoading = false;

                if( status === constants.SUCCESS ) {
                    _state.message = '';
                } else {
                    _state.message = message;
                }

                this.setState( _state );

            }
        } );
    }

    getQuery = () => {
        const _query = new URLSearchParams( this.props.location.search );

        return {
            email: _query.get('email') || '',
            moodType: _query.get('moodType') || '',
            templateId:_query.get('templateId') || ''
        }
    }



    render() {

        return(
            <MailerView mood={ this.state.mood } />
        );
    }
}