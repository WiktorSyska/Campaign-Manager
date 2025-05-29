import React from "react";

const AccountBalance = () => {
    const [balance, setBalance] = React.useState(null);
    const [loading, setLoading] = React.useState(true);

    React.useEffect(() => {
        const fetchBalance = async () => {
            try {
                const response = await api.getAccountBalance();
                setBalance(response.data.data.balance);
            } catch (error) {
                console.error('Error fetching balance:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchBalance();
    }, []);

    return (
        <div className="card">
            <h3>Account Balance</h3>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <p className="display-6">${balance}</p>
            )}
        </div>
    );
};