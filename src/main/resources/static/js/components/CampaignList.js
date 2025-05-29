import React from "react";

const CampaignList = ({ onEditCampaign }) => {
    const [campaigns, setCampaigns] = React.useState([]);
    const [loading, setLoading] = React.useState(true);
    const [error, setError] = React.useState(null);

    const fetchCampaigns = async () => {
        setLoading(true);
        try {
            const response = await api.getCampaigns();
            setCampaigns(response.data.data);
            setError(null);
        } catch (err) {
            setError('Failed to load campaigns');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    React.useEffect(() => {
        fetchCampaigns();
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this campaign?')) {
            try {
                await api.deleteCampaign(id);
                fetchCampaigns();
            } catch (error) {
                console.error('Error deleting campaign:', error);
            }
        }
    };

    if (loading) return <p>Loading campaigns...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="campaign-list">
            <h3>Your Campaigns</h3>
            {campaigns.length === 0 ? (
                <p>No campaigns found. Create your first campaign!</p>
            ) : (
                <div className="table-responsive">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Bid Amount</th>
                            <th>Fund</th>
                            <th>Status</th>
                            <th>Town</th>
                            <th>Radius</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {campaigns.map(campaign => (
                            <tr key={campaign.id}>
                                <td>{campaign.campaignName}</td>
                                <td>${campaign.bidAmount}</td>
                                <td>${campaign.campaignFund}</td>
                                <td>{campaign.status}</td>
                                <td>{campaign.townName}</td>
                                <td>{campaign.radius} km</td>
                                <td>
                                    <button
                                        onClick={() => onEditCampaign(campaign)}
                                        className="btn btn-sm btn-outline-primary me-2"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(campaign.id)}
                                        className="btn btn-sm btn-outline-danger"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};