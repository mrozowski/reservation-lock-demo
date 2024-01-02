import React from "react";
import './Input.css'

const Input = ({label, type, name, value, labelLocation, onChange}) => {
    const setInputStyle = () => {
        let labelOption = "";
        if (labelLocation === "left"){
            labelOption = "input-label-left";
        } else {
            labelOption = "input-label-top";
        }
        return `ui-input ${labelOption}`;
    }

    return (
        <div className={setInputStyle()}>
            <label>{label}</label>
            <input
                type={type}
                name={name}
                value={value}
                onChange={onChange}/>
        </div>
    );
};

export default Input;