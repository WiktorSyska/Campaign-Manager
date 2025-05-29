import React from "react";

const CampaignForm = ({ onCampaignCreated, campaignToEdit }) => {
    const [formData, setFormData] = React.useState({
        campaignName: '',
        bidAmount: '',
        campaignFund: '',
        status: 'ON',
        townId: '',
        radius: '',
        keywordIds: []
    });
    console.log("useEffect triggered");
    const [keywords, setKeywords] = React.useState([]);
    const [towns, setTowns] = React.useState([]);
    const [errors, setErrors] = React.useState({});
    const [isSubmitting, setIsSubmitting] = React.useState(false);
    React.useEffect(() => {
        console.log("useEffect triggered");
        if (campaignToEdit) {
            setFormData({
                campaignName: campaignToEdit.campaignName,
                bidAmount: campaignToEdit.bidAmount.toString().replace('.', ','),
                campaignFund: campaignToEdit.campaignFund.toString().replace('.', ','),
                status: campaignToEdit.status,
                townId: campaignToEdit.town?.id || campaignToEdit.townId,
                radius: campaignToEdit.radius.toString(),
                keywordIds: campaignToEdit.keywords?.map(k => k.id) ||
                    campaignToEdit.keywordIds ||
                    []
            });
        }

        const fetchData = async () => {
            console.log("fetchData called");
            try {
                console.log("fetchData called");
                const [townsRes, keywordsRes] = await Promise.all([
                    api.getTowns(),
                    api.getKeywords()
                ]);

                if (townsRes.data && townsRes.data.success && townsRes.data.data) {
                    setTowns(townsRes.data.data);
                } else {
                    console.error('Invalid towns data structure:', townsRes.data);
                }

                if (keywordsRes.data && keywordsRes.data.success && keywordsRes.data.data) {
                    setKeywords(keywordsRes.data.data);
                } else {
                    console.error('Invalid keywords data structure:', keywordsRes.data);
                }

                $('.select2-keywords').select2();
            } catch (error) {
                console.error('Error loading data:', error);
            }
        };

        fetchData();
    }, [campaignToEdit]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleKeywordsChange = (selectedKeywords) => {
        setFormData(prev => ({ ...prev, keywordIds: selectedKeywords }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setErrors({});

        try {
            const data = {
                ...formData,
                bidAmount: parseFloat(formData.bidAmount),
                campaignFund: parseFloat(formData.campaignFund),
                radius: parseInt(formData.radius),
                keywordIds: formData.keywordIds.map(id => parseInt(id))
            };

            if (campaignToEdit) {
                await api.updateCampaign(campaignToEdit.id, data);
            } else {
                await api.createCampaign(data);
            }

            onCampaignCreated();
            if (!campaignToEdit) {
                setFormData({
                    campaignName: '',
                    bidAmount: '',
                    campaignFund: '',
                    status: 'ON',
                    townId: '',
                    radius: '',
                    keywordIds: []
                });
            }
        } catch (error) {
            if (error.response && error.response.data.errors) {
                setErrors(error.response.data.errors);
            } else {
                console.error('Error submitting form:', error);
            }
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="campaign-form">
            <h3>{campaignToEdit ? 'Edit Campaign' : 'Create New Campaign'}</h3>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Campaign Name</label>
                    <input
                        type="text"
                        name="campaignName"
                        value={formData.campaignName}
                        onChange={handleChange}
                        className={`form-control ${errors.campaignName ? 'is-invalid' : ''}`}
                        required
                    />
                    {errors.campaignName && <div className="invalid-feedback">{errors.campaignName}</div>}
                </div>

                <div className="form-group">
                    <label>Keywords</label>
                    <select
                        multiple
                        className="form-control select2-keywords"
                        value={formData.keywordIds}
                        onChange={(e) => handleKeywordsChange(Array.from(e.target.selectedOptions, option => option.value))}
                    >
                        {keywords.map(keyword => (
                            <option key={keyword.id} value={keyword.id}>{keyword.keywordText}</option>
                        ))}
                    </select>
                    {errors.keywordIds && <div className="invalid-feedback d-block">{errors.keywordIds}</div>}
                </div>

                <div className="form-group">
                    <label>Bid Amount</label>
                    <input
                        type="number"
                        name="bidAmount"
                        value={formData.bidAmount}
                        onChange={handleChange}
                        className={`form-control ${errors.bidAmount ? 'is-invalid' : ''}`}
                        min="0.01"
                        step="0.01"
                        required
                    />
                    {errors.bidAmount && <div className="invalid-feedback">{errors.bidAmount}</div>}
                </div>

                <div className="form-group">
                    <label>Campaign Fund</label>
                    <input
                        type="number"
                        name="campaignFund"
                        value={formData.campaignFund}
                        onChange={handleChange}
                        className={`form-control ${errors.campaignFund ? 'is-invalid' : ''}`}
                        min="0.01"
                        step="0.01"
                        required
                    />
                    {errors.campaignFund && <div className="invalid-feedback">{errors.campaignFund}</div>}
                </div>

                <div className="form-group">
                    <label>Status</label>
                    <select
                        name="status"
                        value={formData.status}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        <option value="ON">ON</option>
                        <option value="OFF">OFF</option>
                    </select>
                </div>

                <div className="form-group">
                    <label>Town</label>
                    <select
                        name="townId"
                        value={formData.townId}
                        onChange={handleChange}
                        className={`form-control ${errors.townId ? 'is-invalid' : ''}`}
                        required
                    >
                        <option value="">Select a town</option>
                        {towns.map(town => (
                            <option key={town.id} value={town.id}>
                                {town.townName} ({town.postalCode})
                            </option>
                        ))}
                    </select>
                    {errors.townId && <div className="invalid-feedback">{errors.townId}</div>}
                </div>

                <div className="form-group">
                    <label>Radius (km)</label>
                    <input
                        type="number"
                        name="radius"
                        value={formData.radius}
                        onChange={handleChange}
                        className={`form-control ${errors.radius ? 'is-invalid' : ''}`}
                        min="1"
                        required
                    />
                    {errors.radius && <div className="invalid-feedback">{errors.radius}</div>}
                </div>

                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                    {isSubmitting ? 'Processing...' : (campaignToEdit ? 'Update Campaign' : 'Create Campaign')}
                </button>
            </form>
        </div>
    );
};