import React from "react";
import './Input.css'
const Input = ({ label, type, name, value, onChange}) => {
    return (
        <div className="ui-input">
            <label>{label}</label>
            <input
                type={type}
                name={name}
                value={value}
                onChange={onChange}
            />
        </div>
    );
};

export default Input;