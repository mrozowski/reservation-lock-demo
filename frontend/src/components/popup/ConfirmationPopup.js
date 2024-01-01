import React from 'react';
import './ConfirmationPopup.css'
import DefaultButton from "../button/DefaultButton";

const ConfirmationPopup = ({text, onCancel, onConfirm}) => {
    const handleCancel = () => {
        onCancel();
    };

    const handleConfirm = () => {
        onConfirm();
    };

    return (
        <section>
            <div className="overlay"></div>
            <div className="confirmation-popup-container ">

                <div className="popup-content main-card-background">
                    <p>{text}</p>
                    <div className="button-container">
                        <div className="">
                            <DefaultButton text="Cancel" clickEvent={handleCancel}/>
                        </div>
                        <div className="margin-left-1">
                            <DefaultButton text="Confirm" clickEvent={handleConfirm}/>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default ConfirmationPopup;
