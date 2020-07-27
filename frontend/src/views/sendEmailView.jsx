import React from 'react';
import { constants } from '../modules'; 
import Template1 from '../images/template1.png';
import Template2 from '../images/template2.png';
import ModalImage from "react-modal-image";
import TemplateSelectView from './templateSelectView';

const templateObject = {
    "1": Template1,
    "2": Template2
};

const sendEmailView = (payload) => {

    const { isLoading, deptLocText, searchText, onSearchChange, filterData, onEmailClick, onValueClick, selectedEmail, onRemoveEmail, onRemoveValue, onSendEmailClick, filterDeptLocData, selectedValue, messageText, templateId, subjectText } = payload;

    const view = (
        <>
            <div className='commonbx__body'>
                <div className='sendEmailForm__column'>
                    <div className='sendEmailForm__searchbx'>
                        <input type='text' className='sendEmailForm__input' placeholder='Search department and location' value={deptLocText} onChange={(e) => onSearchChange({ deptLocText: e.currentTarget.value })} />
                        <ul className='sendEmailForm__list'>
                            {
                                filterDeptLocData.length === 0 ? null : filterDeptLocData.map((filter, index) => {
                                    return (
                                        <li key={index} className='sendEmailForm__item' onClick={() => onValueClick(filter)}>{filter}</li>
                                    );
                                })
                            }
                        </ul>
                    </div>

                    <div className='sendEmailForm__searchbx'>
                        <input type='text' className='sendEmailForm__input' placeholder='Search employee email-Id' value={searchText} onChange={(e) => onSearchChange({ searchText: e.currentTarget.value })} />
                        <ul className='sendEmailForm__list'>
                            {
                                filterData.length === 0 ? null : filterData.map((filter, index) => {
                                    console.log(filterData);
                                    return (
                                        <li key={index} className='sendEmailForm__item' onClick={() => onEmailClick(filter.staffEmailId)}>{filter.staffEmailId}</li>
                                    );
                                })
                            }
                        </ul>
                    </div>

                    <ul className='sendEmailForm__emailList'>
                        {
                            selectedEmail.map((email, index) => {
                                return (
                                    <li key={email} className='sendEmailForm__emailList__item'>
                                        <span>{email}</span>
                                        <span onClick={() => onRemoveEmail(index)}>&#8722;</span>
                                    </li>
                                );
                            })
                        }
                        {
                            selectedValue.map((value) => {
                                return (
                                    <li key={value} className='sendEmailForm__emailList__item'>
                                        <span>{value}</span>
                                        <span onClick={() => onRemoveValue(value)}>&#8722;</span>
                                    </li>
                                );
                            })
                        }
                    </ul>
                </div>
                <div className='sendEmailForm__column'>
                    <input type='text' className='sendEmailForm__input' placeholder='Enter subject' value={subjectText} onChange={(e) => onSearchChange({ subjectText: e.currentTarget.value })} />
                    <input type='text' className='sendEmailForm__input' placeholder='Enter message' value={messageText} onChange={(e) => onSearchChange({ messageText: e.currentTarget.value })} />

                    <TemplateSelectView
                        className='sendEmailForm__input'
                        value={templateId}
                        onChange={(e) => onSearchChange({ templateId: e })}
                    />

                    {
                        templateId.length === 0 ? null :
                        <ModalImage
                            small={templateObject[ templateId ] }
                            large={ templateObject[ templateId ] }
                            alt={ constants.Template_OPTIONS.find( ( i ) => i.id === templateId ).value }
                            className='sendEmailForm__popupImg'
                        />
                    }
                    <button type='button' className='sendEmailForm__btn' onClick={onSendEmailClick} disabled={selectedEmail.length === 0 && selectedValue.length === 0}>Send Email</button>
                </div>
            </div>
        </>
    );

    return (
        <div className='commonbx sendEmailForm'>
            <div className='commonbx__head'>
                <h3 className='commonbx__head__heading'>Send Email</h3>
            </div>
            {isLoading ? <div className='commonbx__body'>Loading...</div> : view}
        </div>
    );
}

export default sendEmailView;