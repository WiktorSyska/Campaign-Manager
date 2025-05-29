import React from "react";

const App = () => {
    const [editingCampaign, setEditingCampaign] = React.useState(null);
    const [refreshKey, setRefreshKey] = React.useState(0);

    const handleCampaignCreated = () => {
        setEditingCampaign(null);
        setRefreshKey(prev => prev + 1);
    };

    const handleEditCampaign = (campaign) => {
        setEditingCampaign(campaign);
        window.scrollTo(0, 0);
    };

    React.useEffect(() => {
        // Initialize Select2 for keywords
        $('.select2-keywords').select2({
            placeholder: "Select keywords",
            allowClear: true,
            ajax: {
                url: '/api/keywords/search',
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return { q: params.term };
                },
                processResults: function (data) {
                    return {
                        results: data.data.map(item => ({
                            id: item.id,
                            text: item.text
                        }))
                    };
                },
                cache: true
            }
        });
    }, [refreshKey]);

    return (
        <div>
            <AccountBalance key={`balance-${refreshKey}`} />
            <CampaignForm
                onCampaignCreated={handleCampaignCreated}
                campaignToEdit={editingCampaign}
                key={`form-${refreshKey}`}
            />
            <CampaignList
                onEditCampaign={handleEditCampaign}
                key={`list-${refreshKey}`}
            />
        </div>
    );
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);