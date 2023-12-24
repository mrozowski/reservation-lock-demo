import React from "react";
import {Link} from 'react-router-dom';
import './TopBar.css'
import logo from '../../assets/logo.svg'

const TopBar = () => {
    return (
        <div className="top-bar">
            <div className="top-bar-container">
                <img src={logo} alt="Your Logo"/>
                <div className="menu-buttons">
                    <Link to="/" className="menu-button">
                        <button>Home</button>
                    </Link>
                    <Link to="/reservation" className="menu-button">
                        <button>Reservation</button>
                    </Link>
                    <Link to="/about" className="menu-button">
                        <button>About</button>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default TopBar;