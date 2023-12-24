import React from 'react';
import './RectangleCard.css';

const RectangleCard = ({ title, price, dateTime, clickEvent }) => {
    return (
        <div className="rectangle-card" onClick={clickEvent}>
            <div className="title">{title}</div>
            <div className="price">{price}</div>
            <div className="date-time">{dateTime}</div>
        </div>
    );
};

export default RectangleCard;