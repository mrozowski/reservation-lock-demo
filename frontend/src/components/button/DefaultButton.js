import React from "react";
import './DefaultButton.css'

const DefaultButton = ({ text, clickEvent }) => {
    return (
        <button onClick={clickEvent} className={"default-button"}>{text}</button>
    );
};

export default DefaultButton;